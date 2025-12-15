import { defineConfig } from "vite";
import scalaJSPlugin from "@scala-js/vite-plugin-scalajs";

export default defineConfig({
  base: '/bin2echo-web/',
  plugins: [scalaJSPlugin()],
});
